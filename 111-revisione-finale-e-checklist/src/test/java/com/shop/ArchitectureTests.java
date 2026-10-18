package com.shop;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.springframework.beans.factory.annotation.Autowired;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noFields;

@AnalyzeClasses(packages = "com.shop", importOptions = ImportOption.DoNotIncludeTests.class)
class ArchitectureTests {
    @ArchTest
    static final ArchRule webDoesNotUseRepositories = noClasses()
            .that().resideInAPackage("com.shop.web..")
            .should().dependOnClassesThat().haveSimpleNameEndingWith("Repository")
            .because("controllers go through a service");

    @ArchTest
    static final ArchRule catalogDoesNotDependOnWeb = noClasses()
            .that().resideInAPackage("com.shop.catalog..")
            .should().dependOnClassesThat().resideInAPackage("com.shop.web..")
            .because("the domain does not know the HTTP layer");

    @ArchTest
    static final ArchRule noFieldInjection = noFields()
            .should().beAnnotatedWith(Autowired.class)
            .because("dependencies arrive through the constructor");
}
