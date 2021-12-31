(defproject com.github.itarck/fancoil.module "0.0.5-SNAPSHOT"
  :description "Modules for fancoil framework "
  :url "https://github.com/itarck/fancoil.module"
  :license {:name "MIT License"
            :url  "https://opensource.org/licenses/MIT"}
  :source-paths ["src/main"]
  :dependencies [[applied-science/js-interop "0.3.1"]
                 [cljs-http/cljs-http  "0.1.46"]
                 [datascript/datascript "1.3.2"]
                 [denistakeda/posh "0.5.9"]
                 [integrant/integrant "0.8.0"]
                 [reagent/reagent "1.1.0"]
                 [cljs-ajax/cljs-ajax "0.8.4"]
                 [metosin/reitit "0.5.15"]
                 [pez/clerk "1.0.0"]
                 [medley/medley  "1.3.0"]
                 [venantius/accountant "0.2.5"]
                 [com.github.itarck/fancoil "0.0.6-SNAPSHOT"]])
