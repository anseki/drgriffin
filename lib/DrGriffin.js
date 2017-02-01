/*
 * DrGriffin
 * https://github.com/anseki/drgriffin
 *
 * Copyright (c) 2017 anseki
 * Licensed under the MIT license.
 */

'use strict';

var
  fs = require('fs'),

  METHODS =
    fs.readFileSync(__dirname + '/DrGriffin-methods.csv', {encoding: 'utf8'}).split(',')
      .reduce(function(METHODS, methodName) {
        METHODS[methodName] = function() { // resolve, reject, arg1, arg2 ...
          var args = Array.prototype.slice.call(arguments),
            resolve = args.shift(), reject = args.shift();
          sendMethodRequest(resolve, reject, methodName, args);
        };
        return METHODS;
      }, {}),

  RE_LINE = /^([^\n\r]*)[\n\r]+([\s\S]*)$/,
  drGriffin = require('child_process').spawn(
    'java', ['-cp', __dirname + '/Util.jar', 'io.github.anseki.drgriffin.util.REPL', 'UTF-8'],
      {stdio: 'pipe'}),
  requests = {}, curRequestId = 0, stdoutData = '', stderrData = '';

function sendMethodRequest(resolve, reject, methodName, args) {
  requests[++curRequestId] = { resolve: resolve, reject: reject };
  args.unshift(curRequestId, methodName);
  drGriffin.stdin.write(args.join('\t') + '\n');
}

function parseResponse(lines, cb) {
  var matches, line, response;
  while ((matches = RE_LINE.exec(lines))) {
    line = matches[1];
    lines = matches[2];
    response = line.split('\t', 2);
    if (response.length < 2) { throw new Error('Invalid response: ' + line); }
    cb(response[0], response[1]);
  }
  return lines;
}

drGriffin.stdout.setEncoding('utf8');
drGriffin.stdout.on('data', function (chunk) {
  stdoutData = parseResponse(stdoutData + chunk, function(requestId, message) {
    if (requestId && requests[requestId]) {
      requests[requestId].resolve(message);
      delete requests[requestId];
    } else {
      // REPL said something irregular, but it may be able to continue.
      console.warn('Unknown or dropped response: %s %s', requestId, message);
    }
  });
});

drGriffin.stderr.setEncoding('utf8');
drGriffin.stderr.on('data', function (chunk) {
  stderrData = parseResponse(stderrData + chunk, function(requestId, message) {
    if (!requestId) { // Fatal error
      requests = {};
      throw new Error(message);
    } else if (requests[requestId]) {
      requests[requestId].reject(message);
      delete requests[requestId];
    } else {
      // It might be response after fatal error.
      console.warn('Unknown or dropped response: %s %s', requestId, message);
    }
  });
});

drGriffin.stdin.on('error', function (error) { throw error; });
drGriffin.stdout.on('error', function (error) { throw error; });
drGriffin.stderr.on('error', function (error) { throw error; });

exports.finish = function() {
  drGriffin.stdin.end();
};

// Static fields
fs.readFileSync(__dirname + '/DrGriffin-fields.csv', {encoding: 'utf8'}).split(',')
  .forEach(function(fieldName) {
    exports[fieldName] = fieldName;
  });

// ================================ Setup Dual APIs
Object.keys(METHODS).forEach(function(methodName) {
  var method = METHODS[methodName];
  exports[methodName] = function() {
    var args = Array.prototype.slice.call(arguments), cb;

    if (args.length && args[args.length - 1] instanceof Function) {

      // Callback
      cb = args.pop();
      args.unshift(
        // resolve: cb(null, rtn1, rtn2 ...)
        function() {
          Array.prototype.unshift.call(arguments, null);
          cb.apply(null, arguments);
        },
        // reject: cb(error, rtn1, rtn2 ...)
        function() { cb.apply(null, arguments); });
      return method.apply(null, args);

    } else {

      // Promise
      return new Promise(function(resolve, reject) {
        args.unshift(resolve, reject);
        method.apply(null, args);
      });

    }
  };
});
