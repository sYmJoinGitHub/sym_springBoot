/**
 * 当过程中没有引入任何第三方缓存时，springBoot默认使用ConcurrentMapCacheManager管理缓存，
 * 底层就是使用CurrentHashMap来存储key-value形式的缓存。
 *
 * 所以想体验CurrentHashMap的缓存形式，需要先将pom.xml文件的redis模块移除
 *
 * @author shenym
 * @date 2020/3/25 10:51
 */

package com.sym.map;