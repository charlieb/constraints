(defproject constraints "0.1.7"
  :description "Particle distance based constraint system"
  :url "https://github.com/charlieb/constraints"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]]
  :repl-options {:init-ns constraints.core}
  :deploy-repositories [["clojars"  {:sign-releases false :url "https://repo.clojars.org"}]])
