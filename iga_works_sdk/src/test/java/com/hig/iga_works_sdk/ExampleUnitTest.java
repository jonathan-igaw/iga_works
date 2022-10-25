package com.hig.iga_works_sdk;

import static org.junit.Assert.*;
import org.junit.Test;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addEvent_isCorrect() {
        String json = "{\"evt\":{\"created_at\":\"20221025115704\",\"event\":\"open_menu\",\"location\":{\"lat\":37.767,\"lng\":-122.42424},\"param\":{\"menu_name\":\"menu1\",\"menu_id\":30},\"user_properties\":{\"birthyear\":1982,\"gender\":\"m\",\"level\":37,\"character_class\":\"knight\",\"gold\":300}},\"common\":{\"identity\":{\"adid\":\"a5f21bc1-4a1d-48e0-8829-6dee007da8c7\",\"adid_opt_out\":false},\"device_info\":{\"os\":\"4.4.4\",\"model\":\"zerry_hp\",\"resolution\":\"1080x1920\",\"is_portrait\":false,\"platform\":\"android\",\"network\":\"wifi\",\"carrier\":\"VODAFONE+TR\",\"language\":\"ko\",\"country\":\"kr\"},\"package_name\":\"com.hig.iga_works\",\"appkey\":\"inqbator@naver.com\"}}";
        assertTrue(IGASDK.addEvent("test_event", json));
    }

    @Test
    public void addEventWithWrongAppKey_isCorrect() {
        String json = "{\"evt\":{\"created_at\":\"20221025115704\",\"event\":\"open_menu\",\"location\":{\"lat\":37.767,\"lng\":-122.42424},\"param\":{\"menu_name\":\"menu1\",\"menu_id\":30},\"user_properties\":{\"birthyear\":1982,\"gender\":\"m\",\"level\":37,\"character_class\":\"knight\",\"gold\":300}},\"common\":{\"identity\":{\"adid\":\"a5f21bc1-4a1d-48e0-8829-6dee007da8c7\",\"adid_opt_out\":false},\"device_info\":{\"os\":\"4.4.4\",\"model\":\"zerry_hp\",\"resolution\":\"1080x1920\",\"is_portrait\":false,\"platform\":\"android\",\"network\":\"wifi\",\"carrier\":\"VODAFONE+TR\",\"language\":\"ko\",\"country\":\"kr\"},\"package_name\":\"com.hig.iga_works\",\"appkey\":\"inqbator@knou.ac.kr\"}}";
        assertFalse(IGASDK.addEvent("test_event", json));
    }
}