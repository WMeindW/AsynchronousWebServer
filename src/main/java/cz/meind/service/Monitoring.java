package cz.meind.service;

import cz.meind.application.Application;

public class Monitoring {
    public void setup() {
        Application.monitor = this;
    }

    public void run() {

    }
}
