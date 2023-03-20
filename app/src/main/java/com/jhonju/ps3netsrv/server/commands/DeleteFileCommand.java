package com.jhonju.ps3netsrv.server.commands;

import com.jhonju.ps3netsrv.server.Context;
import com.jhonju.ps3netsrv.server.exceptions.PS3NetSrvException;

import java.io.IOException;

public class DeleteFileCommand extends FileCommand {

    public DeleteFileCommand(Context ctx, short filePathLength) {
        super(ctx, filePathLength);
    }

    @Override
    public void executeTask() throws PS3NetSrvException, IOException {
        send(getFile().delete() ? SUCCESS_CODE_BYTEARRAY : ERROR_CODE_BYTEARRAY);
    }
}
