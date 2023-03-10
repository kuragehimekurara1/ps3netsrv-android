package com.jhonju.ps3netsrv.server.commands;

import com.jhonju.ps3netsrv.server.Context;

import java.io.IOException;
import java.io.RandomAccessFile;

public class ReadCD2048Command extends AbstractCommand {

    private static final short MAX_RESULT_SIZE = 2048;

    private final int startSector;
    private final int sectorCount;

    public ReadCD2048Command(Context ctx, int startSector, int sectorCount) {
        super(ctx);
        this.startSector = startSector;
        this.sectorCount = sectorCount;
    }

    @Override
    public void executeTask() throws Exception {
        final int MAX_SECTORS = BUFFER_SIZE / MAX_RESULT_SIZE;
        if (sectorCount > MAX_SECTORS) {
            throw new IllegalArgumentException("Too many sectors read!");
        }
        if (ctx.getFile() == null) {
            throw new IllegalArgumentException("File shouldn't be null");
        }
        send(readSectors(ctx.getReadOnlyFile(), startSector * ctx.getCdSectorSize().cdSectorSize, sectorCount));
    }

    private byte[][] readSectors(RandomAccessFile file, long offset, int count) throws IOException {
        final int SECTOR_SIZE = ctx.getCdSectorSize().cdSectorSize;

        byte[][] result = new byte[count][MAX_RESULT_SIZE];
        for (int i = 0; i < count; i++) {
            file.seek(offset + BYTES_TO_SKIP);
            file.read(result[i]);
            offset += SECTOR_SIZE;
        }
        return result;
    }

}
