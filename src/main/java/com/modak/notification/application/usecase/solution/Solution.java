package com.modak.notification.application.usecase.solution;

import com.modak.notification.infraestructure.gateway.Gateway;
import com.modak.notification.application.usecase.NotificationUseCase;


class Solution {

    public static void main(String[] args) {

        NotificationUseCase service = new NotificationUseCase(new Gateway());

        service.send("news", "user", "news 1");

        service.send("news", "user", "news 2");

        service.send("news", "user", "news 3");

        service.send("news", "another user", "news 1");

        service.send("update", "user", "update 1");

    }

}


