package cn.mezeron.common.util.uuid;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

public class Base64UuidGenerator implements IdentifierGenerator {
	
    public Serializable generate(SessionImplementor session, Object object) throws HibernateException {
        return UuidUtils.compressedUuid();
    }
    
}