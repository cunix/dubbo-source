package com.alibaba.dubbo.common.extension;

/**
 * ExtensionFactory
 * 
 * @author william.liangf
 * @export
 */
@SPI
public interface ExtensionFactory {

    /**
     * Get extension.
     * 
     * @para m type object type.
     * @param name object name.
     * @return object instance.
     */
    <T> T getExtension(Class<T> type, String name);

}
