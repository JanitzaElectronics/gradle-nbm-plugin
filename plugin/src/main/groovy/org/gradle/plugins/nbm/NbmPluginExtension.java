package org.gradle.plugins.nbm;

import groovy.lang.Closure;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public final class NbmPluginExtension {
    private String moduleName;
    private String cluster;
    private String specificationVersion;
    private String implementationVersion;
    private boolean eager;
    private boolean autoload;
    private final NbmKeyStoreDef keyStore;
    private final Project project;
    private final List<String> requires;
    private String localizingBundle;
    private String moduleInstall;
    private final NbmFriendPackages friendPackages;

    private final Configuration harnessConfiguration;

    public NbmPluginExtension(Project project) {
        Objects.requireNonNull(project, "project");

        this.harnessConfiguration = project.getConfigurations().detachedConfiguration(
                project.getDependencies().create("org.codehaus.mojo:nbm-maven-harness:8.1"));

        this.moduleName = null;
        this.cluster = null;
        this.specificationVersion = null;
        this.implementationVersion = null;
        this.localizingBundle = null;
        this.moduleInstall = null;
        this.eager = false;
        this.autoload = false;
        this.friendPackages = new NbmFriendPackages();
        this.keyStore = new NbmKeyStoreDef();
        this.requires = new LinkedList<>();
        this.project = project;
    }

    public NbmFriendPackages getFriendPackages() {
        return friendPackages;
    }

    public void friendPackages(Closure<NbmFriendPackages> configBlock) {
        configBlock.setResolveStrategy(Closure.DELEGATE_FIRST);
        configBlock.setDelegate(friendPackages);
        configBlock.call(friendPackages);
    }

    public Configuration getHarnessConfiguration() {
        return harnessConfiguration;
    }

    public String getModuleInstall() {
        return moduleInstall;
    }

    public void setModuleInstall(String moduleInstall) {
        this.moduleInstall = moduleInstall;
    }

    public String getLocalizingBundle() {
        return localizingBundle;
    }

    public void setLocalizingBundle(String localizingBundle) {
        this.localizingBundle = localizingBundle;
    }

    public List<String> getRequires() {
        return requires;
    }

    public void setRequires(List<String> requires) {
        Objects.requireNonNull(requires, "requires");
        this.requires.clear();
        this.requires.addAll(requires);
    }

    public void requires(String dependency) {
        requires.add(dependency);
    }

    public void keyStore(Closure<NbmKeyStoreDef> configBlock) {
        configBlock.setResolveStrategy(Closure.DELEGATE_FIRST);
        configBlock.setDelegate(keyStore);
        configBlock.call(keyStore);
    }

    public NbmKeyStoreDef getKeyStore() {
        return keyStore;
    }

    public String getModuleName() {
        if (moduleName == null) {
            return project.getName();
        }
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getCluster() {
        return cluster;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
    }

    public String getSpecificationVersion() {
        if (specificationVersion == null) {
            return EvaluateUtils.asString(project.getVersion());
        }
        return specificationVersion;
    }

    public void setSpecificationVersion(String specificationVersion) {
        this.specificationVersion = specificationVersion;
    }

    public String getImplementationVersion() {
        return implementationVersion;
    }

    public void setImplementationVersion(String implementationVersion) {
        this.implementationVersion = implementationVersion;
    }

    public boolean isEager() {
        return eager;
    }

    public void setEager(boolean eager) {
        this.eager = eager;
    }

    public boolean isAutoload() {
        return autoload;
    }

    public void setAutoload(boolean autoload) {
        this.autoload = autoload;
    }
}
