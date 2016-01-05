# DrGriffin - Node.js

## Installation

```shell
npm install drgriffin
```

## Methods

By loading the module, all methods shown in [Java.md#methods](Java.md#methods) can be used. Also, all names of constant values can be specified.  
For example:

```js
var drGriffin = require('drgriffin');
drGriffin.moveMouse(500, 800, 600, drGriffin.TF_EASE);
drGriffin.finish();
```

Note that a `finish` method has to be called to make the process exit after all actions were done.

These methods except `finish` method support Dual APIs. If a callback Function is appended to the arguments of a method, the method works as callback API. Otherwise the method returns a [`Promise`](https://developer.mozilla.org/en/docs/Web/JavaScript/Reference/Global_Objects/Promise) instance.

### Callback

This is legacy standard Node.js style. A callback Function that was appended to the arguments of a method is called when the method finished. If an error occurred, an `Error` object is passed to the callback Function.

For example:

```js
drGriffin.moveMouse(1000, 150, 100, function(error) {
  if (error) {
    console.error(error);
    process.exti(1);
  }

  drGriffin.moveMouse(500, 600, 200, null, function(error) {
    if (error) {
      console.error(error);
      process.exti(1);
    }

    drGriffin.pressButton(500, drGriffin.BUTTON_1, true, function(error) {
      if (error) {
        console.error(error);
        process.exti(1);
      }

      drGriffin.finish();
      // Do something...
    });
  });
});
```

### Promise

This is an alternative way to approach asynchronous that avoids the many nested callbacks.  
For example, this works same as above sample code:

```js
drGriffin.moveMouse(1000, 150, 100).then(function() {
  return drGriffin.moveMouse(500, 600, 200, null);
}).then(function() {
  return drGriffin.pressButton(500, drGriffin.BUTTON_1, true);
}).then(function() {
  drGriffin.finish();
  // Do something...
}).catch(function(error) {
  console.error(error);
  process.exti(1);
});
```

### No Error Handling

Actually, all actions except `finish` method that are requested are queued, and these run in order. Therefore, if you don't need handling a error, you can call the methods in succession.  
For example:

```js
drGriffin.moveMouse(1000, 150, 100);
drGriffin.moveMouse(500, 600, 200, null);
drGriffin.pressButton(500, drGriffin.BUTTON_1, true, function() {
  drGriffin.finish();
  // Do something...
});
```
