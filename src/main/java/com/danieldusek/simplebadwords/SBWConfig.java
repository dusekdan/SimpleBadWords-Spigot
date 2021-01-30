package com.danieldusek.simplebadwords;

import com.danieldusek.simplebadwords.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class SBWConfig {
    final private SimpleBadWords instance;

    public SBWConfig(SimpleBadWords instance) {
        this.instance = instance;
        this.load();
    }

    public void load(){
        this.instance.reloadConfig();

        // Manual reload of cached normalized words
        this.level1Words();
        this.level2Words();
    }

    public List<String> level2WordsNormalized = new ArrayList<>();
    public List<String> level1WordsNormalized = new ArrayList<>();

    public List<String> level1Words(){
        level1WordsNormalized.clear();
        for (String word : this.instance.getConfig().getStringList("words.level1")) {
            level1WordsNormalized.add(
                    StringUtils.unaccent(word)
            );
        }
        return this.instance.getConfig().getStringList("words.level1");
    }


    public List<String> level2Words() {
        level2WordsNormalized.clear();
        for (String word : this.instance.getConfig().getStringList("words.level2")) {
            level2WordsNormalized.add(
                    StringUtils.unaccent(word)
            );
        }
        return this.instance.getConfig().getStringList("words.level2");
    }


    public List<String> level1Punishments() {
        return this.instance.getConfig().getStringList("punishments.level1");
    }


    public List<String> level2Punishments() {
        return this.instance.getConfig().getStringList("punishments.level2");
    }

}
