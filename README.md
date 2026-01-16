# ForceCape

[![Allay](https://img.shields.io/badge/Allay-0.22.0-blue)](https://github.com/AllayMC/Allay)
[![Java](https://img.shields.io/badge/Java-21+-orange)](https://www.oracle.com/java/)
[![License](https://img.shields.io/badge/License-MIT-green)](LICENSE)

An AllayMC plugin that forces all players to wear a custom cape.

## Features

- Automatically applies cape when players join the server
- Supports custom cape images
- Lightweight with zero configuration

## Prerequisites

- Java 21 or higher
- [AllayMC](https://github.com/AllayMC/Allay) server

## Installation

1. Download the latest release from [Releases](https://github.com/smartcmd/ForceCape/releases)
2. Place the `.jar` file into your server's `plugins` directory
3. Start the server to generate the plugin data folder

## Usage

1. Prepare a cape image (PNG format, recommended size: 64x32)
2. Name the image `cape.png`
3. Place it in the `plugins/ForceCape/` directory
4. Restart the server or reload the plugin

```
plugins/
└── ForceCape/
    └── cape.png    <-- Place here
```

## Building

```bash
./gradlew build
```

The build output will be located in `build/libs/`.

## License

This project is licensed under the [MIT License](LICENSE).