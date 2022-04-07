# nnbuilder ![Build Status](https://img.shields.io/circleci/build/github/hse-mentorship/nnbuilder?style=flat-square) 
![Lines of Code](https://img.shields.io/tokei/lines/github/hse-mentorship/nnbuilder?style=flat-square)

## Development setup

0. Install JDK 17, Gradle, Node, Docker; alias `./gradlew` to `gw`
1. Clone the repository
2. Write a `.env` config file (or copy from `.dev.env` example)
3. Run `gw idea assemble` to prepare the Intellij project and build intermediate files
4. Run `gw dockerComposeUp` to enable proxy
5. Open `nnbuilder.ipr` file in Intellij (on macOS run `open *.ipr`)

## Install database on MAC
 * brew install postgres
 * pg_ctl -D /usr/local/var/postgres start
 * psql postgres

### Helpful commands:

* `gw build` performs a full build
* `gw nnbuild-api:assemble` re-generates grpc code from protobuf definitions
* `gw spotlessApply` applies code style fixes
* `gw dockerComposeDown` to disable proxy
