package one.kii.statemate.core.spi;

import lombok.Data;

/**
 * Created by WangYanJiong on 4/7/17.
 */
public interface SaveStateSpi {


    <T> void save(String group, T object);

    @Data
    class Receipt {

    }
}
