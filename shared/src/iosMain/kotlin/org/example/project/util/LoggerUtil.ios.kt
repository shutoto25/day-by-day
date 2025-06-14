package org.example.project.util

import platform.Foundation.NSLog

internal actual fun platformLog(level: String, tag: String, message: String) {
    // iOSではNSLogを使用
    NSLog("[%@] %@", tag, message)
}