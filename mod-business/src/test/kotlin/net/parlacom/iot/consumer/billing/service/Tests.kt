/*
 * This file is property of the Parlacom Telecommunications Brazil
 * This computer program is protected by copyright law and international treaties. Unauthorized reproduction
 * or distribution of this program, or any portion of it, may result in severe civil criminal penalties,
 * and will be prosecuted to the maximum extent possible under the law.
 */
package net.parlacom.iot.consumer.billing.service

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Configuration
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration

@SpringBootTest(classes = [Tests::class])
@ActiveProfiles(profiles = ["test"])
@ContextConfiguration
class Tests {
    @Test
    fun test1() {
        assert(true)
    }

    /**
     * Static inner configuration class that will be automatically used
     * to load the ApplicationContext for the test class
     */
    @Configuration
    internal class Config{
    }
}