package com.xpert.enums;

public enum MessageStatus {
    SENT,       // Message sent (stored) but not yet confirmed delivered
    DELIVERED,  // Message delivered to the recipient's device/inbox
    SEEN        // Message read by the recipient (seen/opened)
}
