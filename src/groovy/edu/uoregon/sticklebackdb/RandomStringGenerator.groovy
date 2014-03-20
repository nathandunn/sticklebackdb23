package edu.uoregon.sticklebackdb

import org.apache.commons.lang.RandomStringUtils

/**
 */
class RandomStringGenerator {

    static String getRandomString(params){
        def length = params?.length ?: 5
        String randomString = RandomStringUtils.random(length, true, false)
        return randomString
    }

    static String getRandomEmail(params) {
        return "${getRandomString(params)}@${getRandomString(params)}.${getRandomString(params)}"
    }

    static String getRandomUrl(params) {
        return "http://${getRandomString(params)}.com}"
    }
}

