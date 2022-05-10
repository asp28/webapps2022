package com.webapps2022.thrift;

import datetime.dateTimeService;
import java.util.Date;
import org.apache.thrift.TException;

public class DateTimeHandler implements dateTimeService.Iface {

    @Override
    public long getDateTime() throws TException {
        return new Date().getTime();
    }

}
