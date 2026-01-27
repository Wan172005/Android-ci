package com.example.lifecycle;

import static org.junit.Assert.assertTrue;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.BySelector;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.Until;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class AppLifeCycleTest {

    @Test
    public void testUserInteractionAndExit() throws InterruptedException {
        // 1. Khởi tạo
        UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        String packageName = InstrumentationRegistry.getInstrumentation().getTargetContext().getPackageName();

        // Thời gian chờ mặc định (ms)
        long TIMEOUT = 5000;

        try (ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class)) {

            // -----------------------------------------------------------
            // BƯỚC 1: KIỂM TRA MỞ APP
            // -----------------------------------------------------------

            BySelector exitButtonSelector = By.res(packageName, "button1");

            // Đợi nút Exit hiện ra (Dấu hiệu App đã load xong UI)
            boolean isAppVisible = device.wait(Until.hasObject(exitButtonSelector), TIMEOUT);
            assertTrue("App không mở lên được (Không thấy nút Exit)", isAppVisible);


            // -----------------------------------------------------------
            // BƯỚC 2: TƯƠNG TÁC NHẬP LIỆU
            // -----------------------------------------------------------
            UiObject2 editText = device.findObject(By.res(packageName, "editText1"));
            // Fallback tìm theo hint text nếu ID lỗi
            if (editText == null) editText = device.findObject(By.textContains("Pick background"));

            assertTrue("Không tìm thấy ô nhập liệu", editText != null);
            editText.setText("blue");

            // Tìm SpyBox (TextView hiển thị kết quả)
            UiObject2 spyBox = device.findObject(By.res(packageName, "textView1"));
            if (spyBox == null) spyBox = device.findObject(By.textContains("spy box"));

            assertTrue("Không tìm thấy SpyBox", spyBox != null);

            // Đợi 1 xíu cho UI cập nhật text mới
            device.waitForIdle(1000);

            // Kiểm tra Logic
            assertTrue("Lỗi Logic: Nhập 'blue' nhưng SpyBox không hiện 'blue'",
                    spyBox.getText().toLowerCase().contains("blue"));


            // -----------------------------------------------------------
            // BƯỚC 3: KIỂM TRA TẮT APP
            // -----------------------------------------------------------
            // Click nút Exit
            device.findObject(exitButtonSelector).click();

            // Đợi cho đến khi toàn bộ giao diện của Package này biến mất khỏi màn hình
            BySelector appPackageSelector = By.pkg(packageName);
            boolean isAppGone = device.wait(Until.gone(appPackageSelector), TIMEOUT);

            assertTrue("Lỗi: Bấm Exit nhưng App vẫn chưa đóng hoàn toàn", isAppGone);
        }
    }
}