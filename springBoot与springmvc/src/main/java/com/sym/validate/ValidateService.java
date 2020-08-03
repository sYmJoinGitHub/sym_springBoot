package com.sym.validate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * @author shenyanming
 * @date 2020/8/3 22:07.
 */
@Service
@Slf4j
public class ValidateService {

    @Qualifier("simpleValidator")
    @Autowired
    private Validator validator;

    public <T> List<String> validate(T model) {
        Set<ConstraintViolation<Object>> set = validator.validate(Objects.requireNonNull(model));
        return set.stream().map(violation -> {
            log.info("错误信息：{}", violation);
            return violation.getMessage();
        }).collect(Collectors.toList());
    }
}
