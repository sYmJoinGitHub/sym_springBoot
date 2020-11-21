package com.sym.bboss.util;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * ES元数据
 *
 *
 * Created by shenym on 2019/8/23.
 */
@Data
@ToString
public class BaseEsMetadata implements Serializable {

    private static final long serialVersionUID = -2093110203602931572L;

    private String _id;
    private String _index;
    private String _type;
    private Long _version;
    private Object _routing;

}
