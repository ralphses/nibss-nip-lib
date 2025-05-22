package ng.com.ninepsb.nibss_nip_lib.annotations;

import ng.com.ninepsb.nibss_nip_lib.model.NipTransaction;
import ng.com.ninepsb.nibss_nip_lib.model.response.NipResponse;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface NipRequestItem {
    Class<? extends NipTransaction> request();
    Class<? extends NipResponse> response();
}
