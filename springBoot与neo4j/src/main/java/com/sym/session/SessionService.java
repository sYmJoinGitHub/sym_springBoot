package com.sym.session;

import org.neo4j.ogm.model.Result;
import org.neo4j.ogm.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author shenym
 * @date 2020/3/25 17:29
 */
@Service
public class SessionService {

    @Autowired
    private Session session;

    public void query(){
        String cql = "";
        Result result = session.query(cql, null);
        result.iterator().forEachRemaining(data->{
            String s = data.toString();
            System.out.println(s);
        });
    }
}
