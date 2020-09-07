# Create Expo CLJS App

*Create Expo ClojureScript apps with a single command.*<br>
This project focuses on making it easy for JS and React developers to get started with native mobile ClojureScript development.

If something doesn’t work, please [file an issue](https://github.com/jgoodhcg/create-expo-cljs-app/issues/new).<br>

Inspired by [create-cljs-app](https://github.com/filipesilva/create-cljs-app) (for web) and [reagent-expo](https://github.com/thheller/reagent-expo).

## Quick Overview

```sh
yarn create expo-cljs-app my-app
cd my-app
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

### Requirements

**Expo Client and Expo CLI tools**. Find those [here](https://expo.io/tools).

**Node 10.16.0 or later version** You can use [nvm](https://github.com/creationix/nvm#installation) (macOS/Linux) or [nvm-windows](https://github.com/coreybutler/nvm-windows#node-version-manager-nvm-for-windows) to easily switch Node versions between different projects.

**[Yarn](https://yarnpkg.com/getting-started/install)**

**[Java](https://adoptopenjdk.net/)**

### To create a new app:

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
