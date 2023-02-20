package com.barun.fabric.portfoliooverlap.util;

import com.barun.fabric.portfoliooverlap.model.MutualFund;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class FileReaderUtilTest {

    @Test
    void shouldThrowRuntimeExceptionIfWrongFilePathProvided() {
        assertThrows(RuntimeException.class, ()
                -> FileReaderUtil.readJson("wrongFile.json", MutualFund.class));
    }
}