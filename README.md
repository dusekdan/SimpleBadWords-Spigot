# AlfaMine's SimpleBadWords Plugin

Very simple and lightweight spigot plugin to discourage players from using specific /offensive/ words. Not highly customizable, but customizable enough.

This is **not a silver bullet** to solve the issue of players using offensive language - they are smart and they will always find a more creative way to be offensive and toxic to each other. The idea here is to have two levels of offenses with a possibility of configuring punishment for each of these levels. See config: 

```yml
punishments:
  level1:
    - "eco take %player% 500"
    - "say &4Player &e%player%&4 was fined &e$500&4 for use of the &e 1st level&4 offense."
  level2:
    - "eco take %player% 1000"
    - "say &4Player &e%player%&4 was fined &e$1000&4 for use of the &e 1st level&4 offense."
words:
  level1:
    - debil
    - idiot
    - shit
  level2:
    - cunt
    - fucktard
```

- :information_source: You can add commands to be executed on player punishment.
- :information_source: More specific you are about the word, the better. The check itself is pretty naive - if punished character sequence is found in players message, player will be fined.
- :information_source: To make either words or punishment commands "empty" (do nothing, punish for nothing), just do `level1: []` or `level2: []` respectively.
- :x: You cannot add punishment or offense levels.

## How to properly define offesnes

**There are two rules to that**:

- No general / incomplete / stem-based offenses (you will generate a lot of false positives)
- The more specific word, the better.

Perfect examples of such words are in the config above. 

No one ever accidentally says "hey, who's the fucktard that runs this server?" instead of "Can I talk to the owner, please?"

## Inspect word implementation

This plugin was prepared for server populated mostly by czech and slovak speakers, so the logic to unaccent letters is implemented. I understand that this may not be suitable for everyone's use case (and since I have no ambition of making this plugin available on spigot store, I will not implement this as a config option).

You can, however, disable this prior to building the plugin in `SimpleBadWords.java:inspectMessage` \[[link](https://github.com/dusekdan/SimpleBadWords-Spigot/blob/master/src/main/java/com/danieldusek/simplebadwords/SimpleBadWords.java#L86)\] by removing the call to `StringUtils.unaccent()`.

If you happen significantly improve the effectivness of `SimpleBadWords.inspectMessage()` method, please do not hesitate to start a PR. While the current implementation solves my immediate problem, there's much to improve there.  