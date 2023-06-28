package com.mytech.order.exception.handler;

import com.mytech.order.exception.traits.CustomerOrderTrait;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * @author SOMISV
 */
@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class OrderTrackingExceptionHandler implements CustomerOrderTrait {}
