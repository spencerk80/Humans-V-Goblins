package com.github.spencerk;

import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@SelectPackages({
        "com.github.spencerk.inventory", "com.github.spencerk.map", "com.github.spencerk.models",
        "com.github.spencerk.prompt"
})
@SuiteDisplayName("Human V Goblin Functional Tests")
@Suite
public class GameTest {
}
