# fancoil.module

Modules for fancoil framework. Modules will be changed more than the framework, so put them in a separate repo.

## Introduction

- fancoil.module.datascript：[datascript],  an alternative to ratom
- fancoil.module.posh：poshed datascript，you can use [posh] in subscribe
- fancoil.module.cljs-ajax: a wrap for [cljs-ajax], as plugin of fancoil unit do!
- fancoil.module.reitit.html-router: a [reitit] frontend router, already integranted with [accountant] and [clerk], originally from [reagent-template]

[posh]:https://github.com/denistakeda/posh
[datascript]:https://github.com/tonsky/datascript
[cljs-ajax]:https://github.com/JulianBirch/cljs-ajax
[reitit]:https://github.com/metosin/reitit
[clerk]:https://github.com/PEZ/clerk
[accountant]:https://github.com/venantius/accountant
[reagent-template]:https://github.com/reagent-project/reagent-template

## Installation

Leiningen/Boot

    [com.github.itarck/fancoil.module "0.0.6-SNAPSHOT"]

Clojure CLI/deps.edn

    com.github.itarck/fancoil.module {:mvn/version "0.0.6-SNAPSHOT"}


## How to Use

You can find some examples in [fancoil-example] repo for reference

- [simple-html-router]: use fancoil.module.reitit/html-router
- [todomvc-posh]: use fancoil.module.posh
- [catchat-full]: use fancoil.module.datascript, fancoil.module.cljs-ajax
- [realworld-demo]: use fancoil.module.cljs-ajax, fancoil.module.reitit/html-router

[fancoil-example]:https://github.com/itarck/fancoil-example
[simple-html-router]:https://github.com/itarck/fancoil-example/tree/main/simple_html_router
[todomvc-posh]:https://github.com/itarck/fancoil-example/tree/main/todomvc-posh
[catchat-full]:https://github.com/itarck/fancoil-example/tree/main/catchat-full
[realworld-demo]:https://github.com/itarck/fancoil-example/tree/main/realworld