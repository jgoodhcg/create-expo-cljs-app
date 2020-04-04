# Create Expo CLJS App


Create Expo ClojureScript apps with a single command.<br>
It is focused on making it easy for JS and React developers to get started with ClojureScript.

Create Expo CLJS App works on Windows, Linux, and macOS.<br>
If something doesn’t work, please [file an issue](https://github.com/jgoodhcg/create-expo-cljs-app/issues/new).<br>

Inspired by [create-cljs-app](https://github.com/filipesilva/create-cljs-app) and [reagent-expo](https://github.com/thheller/reagent-expo).

## Quick Overview

```sh
npx create-cljs-app my-app
cd my-app
npm start
```

Then start the Shadow compiler.
```
shadow-cljs watch app
```
In another terminal start the JavaScript bundler.
```
yarn start
```

When you’re ready to deploy to production, compile cljs for production release.
```
shadow-clj release app
```
And then follow expo instructions to [make a build](https://docs.expo.io/versions/latest/distribution/building-standalone-apps/) or [OTA update](https://docs.expo.io/versions/latest/guides/configuring-ota-updates/).

## Creating an App

**You’ll need to have Node 10.16.0 or later version on your local development machine** (but it’s not required on the server). You can use [nvm](https://github.com/creationix/nvm#installation) (macOS/Linux) or [nvm-windows](https://github.com/coreybutler/nvm-windows#node-version-manager-nvm-for-windows) to easily switch Node versions between different projects.

**You'll also need a [Java SDK](https://adoptopenjdk.net/) (Version 8+, Hotspot).**

To create a new app, you may choose one of the following methods:

### npx

```sh
npx create-expo-cljs-app my-app
```
_([npx](https://medium.com/@maybekatz/introducing-npx-an-npm-package-runner-55f7d4bd282b) comes with npm 5.2+ and higher)_

### npm

```sh
npm init expo-cljs-app my-app
```

_`npm init <initializer>` is available in npm 6+_

### Yarn

```sh
yarn create expo-cljs-app my-app
```

_`yarn create` is available in Yarn 0.25+_

It will create a directory called `my-app` inside the current folder.<br>
Inside that directory, it will generate the initial project structure and install the transitive dependencies:

```
my-app
├── app.json
├── assets
│   ├── icon.png
│   ├── shadow-cljs.png
│   └── splash.png
├── babel.config.js
├── externs
│   └── app.txt
├── package.json
├── README.md
├── shadow-cljs.edn
├── src
│   ├── app
│   │   ├── db.cljs
│   │   ├── handlers.cljs
│   │   ├── handlers_test.cljs
│   │   ├── helpers.cljs
│   │   ├── index.cljs
│   │   ├── subscriptions.cljs
│   │   └── subscriptions_test.cljs
│   └── reagent
│       └── dom.cljs
└── yarn.lock
```

## License

Create Expo CLJS App is open source software [licensed as MIT](https://github.com/jgoodhcg/create-expo-cljs-app/blob/master/LICENSE.md).
