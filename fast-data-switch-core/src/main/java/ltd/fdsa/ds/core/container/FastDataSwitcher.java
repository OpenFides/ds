package ltd.fdsa.ds.core.container;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FastDataSwitcher {
    // step 1 初始化引擎,扫描配置文件中的插件
    static final PluginManager pluginManager = PluginManager.getDefaultInstance();

    static {

        pluginManager.scan("./plugins");
    }

    public static PluginManager getPluginManager() {
        return pluginManager;
    }
}