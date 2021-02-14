package moe.ksmz.rodentraid.sck.Service.Contracts;

import java.io.IOException;

public interface Loadable<T> {
    void loadEntries() throws IOException;
}
