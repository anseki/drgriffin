# DrGriffin - Command Line Tool

## Setup

Add a path of `drgriffin` directory to a `PATH` environment variable of your system.

And, a following command is required for the system that is not Windows.

```shell
chmod +x drgriffin/drgriffin
```

## Commands

```shell
drgriffin <command>[ <arg1>[ <arg2> ...]]
```

One of the following commands and arguments are specified to `<command>` and `<arg*>`.

### `exec`

```shell
drgriffin exec path/to/methods.yml
```

Execute methods written in specific file.  
This file is [YAML](http://yaml.org/) that is human readable and writable format.  
All methods shown in [Java.md#methods](Java.md#methods) can be included. Also, all names of constant values can be specified.  
For example:

```yaml
- [ moveMouse, 1000, 150, 100, TF_LINEAR ]
- [ pressButton, 500, BUTTON_1, true ]
```

#### Structure of YAML file

Top-level-structure must be sequence structure.

```yaml
- <method>
- <method>
    :
    :
```

Each `<method>` element can be one of following structure.

- **Mapping**

```yaml
- method: <method-name>
  <arg1-name>: <arg1-value>
  <arg2-name>: <arg2-value>
    :
    :
```

Or, has `args` element as mapping structure:

```yaml
- method: <method-name>
  args:
    <arg1-name>: <arg1-value>
    <arg2-name>: <arg2-value>
      :
      :
```

Or, has `args` element as sequence structure:

```yaml
- method: <method-name>
  args:
    - <arg1-value>
    - <arg2-value>
        :
        :
```

For example:

```yaml
- method: moveMouse
  delay: 500
  dx: 300
  dy: 100
  speed: SPEED_LOW1

- method: moveMouse
  args:
    - 500
    - 300
    - 100
    - SPEED_LOW1

# You might like a flow style.
- { method: moveMouse, delay: 1000, dx: 150, dy: 100 }
- { method: moveMouse, delay: 500, dx: 600, dy: 200, timing: null }
- { method: pressButton, delay: 500, button: BUTTON_1, andRelease: true }
```

- **Sequence**

```yaml
-
  - <method-name>
  - <arg1-value>
  - <arg2-value>
      :
      :
```

For example:

```yaml
-
  - moveMouse
  - 500
  - 300
  - 100
  - SPEED_LOW1

# You might like a flow style.
- [ moveMouse, 1000, 150, 100 ]
- [ moveMouse, 500, 600, 200, null ]
- [ pressButton, 500, BUTTON_1, true ]
```

### `pointer-xy`

```shell
drgriffin pointer-xy
```

Get current coordinates of the mouse pointer.  
This is used to help that you make plan of action.  
Move a mouse pointer onto a point you need know, and do this command. It returns the coordinates, and it saves the coordinates to your clipboard.  
For example:

```shell
$ drgriffin pointer-xy
[pointer-xy] (X, Y): 534, 498
It was saved to clipboard.
$ 
```
