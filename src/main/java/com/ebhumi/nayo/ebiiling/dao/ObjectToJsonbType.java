package com.ebhumi.nayo.ebiiling.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.SerializationException;
import org.hibernate.usertype.UserType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.*;
import java.util.stream.Collectors;

public class ObjectToJsonbType implements UserType {

    private static ObjectMapper objectMapper;

    protected static ObjectMapper getObjectMapper() {
        if (null == objectMapper) {
            synchronized (ObjectToJsonbType.class) {
                if (null == objectMapper) {
                    final Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();

                    final ObjectMapper objectMapper = builder.build();
                    objectMapper.enableDefaultTypingAsProperty(ObjectMapper.DefaultTyping.JAVA_LANG_OBJECT, ObjectInfoMixIn.PROPERTY_NAME);

                    objectMapper.addMixIn(Object.class, ObjectInfoMixIn.class);

                    ObjectToJsonbType.objectMapper = objectMapper;
                }
            }
        }

        return objectMapper;
    }

    @Override
    public int[] sqlTypes() {
        return new int[] { Types.JAVA_OBJECT };
    }

    @Override
    public Class<Object> returnedClass() {
        return Object.class;
    }

    @Override
    public boolean equals(Object o, Object o1) throws HibernateException {
        if (o == null) {
            return o1 == null;
        }

        return o.equals(o1);
    }

    @Override
    public int hashCode(Object o) throws HibernateException {
        assert (o != null);

        return o.hashCode();
    }

    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] strings, SessionImplementor sessionImplementor, Object o) throws HibernateException, SQLException {
        final String cellContent = resultSet.getString(strings[0]);

        if (null == cellContent) {
            return null;
        }

        try {
            return getObjectMapper().readValue(cellContent, Object.class);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to get JSONB value", e);
        }
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Object o, int i, SessionImplementor sessionImplementor) throws HibernateException, SQLException {
        if (null == o) {
            preparedStatement.setNull(i, Types.OTHER);
        } else {
            final String json;

            try {
                json = getObjectMapper().writeValueAsString(o);
            } catch (JsonProcessingException e) {
                throw new IllegalArgumentException("Failed to set JSONB value", e);
            }

            preparedStatement.setObject(i, json, Types.OTHER);
        }
    }

    @Override
    public Object deepCopy(Object o) throws HibernateException {
        if (!(o instanceof Collection)) {
            return o;
        }

        final Collection<?> collection = (Collection) o;
        final Collection collectionClone = CollectionFactory.newInstance(collection.getClass());

        collectionClone.addAll(collection.stream().map(this::deepCopy).collect(Collectors.toList()));

        return collectionClone;
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public Serializable disassemble(Object o) throws HibernateException {
        final Object deepCopy = deepCopy(o);

        if (!(deepCopy instanceof Serializable)) {
            throw new SerializationException(String.format("%s is not serializable class", o), null);
        }

        return (Serializable) deepCopy;
    }

    @Override
    public Object assemble(Serializable serializable, Object o) throws HibernateException {
        return deepCopy(serializable);
    }

    @Override
    public Object replace(Object o, Object o1, Object o2) throws HibernateException {
        return deepCopy(o);
    }

    private static final class CollectionFactory {

        static <E, T extends Collection<E>> T newInstance(final Class<T> collectionClass) {

            if (List.class.isAssignableFrom(collectionClass)) {
                return (T) new ArrayList<E>();
            } else if (Set.class.isAssignableFrom(collectionClass)) {
                return (T) new HashSet<E>();
            } else {
                throw new IllegalArgumentException("Unsupported collection type : " + collectionClass);
            }
        }
    }
}
