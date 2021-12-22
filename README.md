# fancoil.module

Modules for fancoil framework. Modules will be changed more than the framework, so put them in a separate repo.

## Introduction

- fancoil.module.datascript：[datascript],  an alternative to ratom
- fancoil.module.posh：poshed datascript，you can use [posh] in subscribe

[posh]:https://github.com/denistakeda/posh
[datascript]:https://github.com/tonsky/datascript

## Installation

Leiningen/Boot

    [com.github.itarck/fancoil.module "0.0.3-SNAPSHOT"]

Clojure CLI/deps.edn

    com.github.itarck/fancoil.module {:mvn/version "0.0.3-SNAPSHOT"}


## How to Use

Here is a [todomvc example] to use poshed datascript

[todomvc example]:https://github.com/itarck/fancoil-example/tree/main/todomvc-datascript
