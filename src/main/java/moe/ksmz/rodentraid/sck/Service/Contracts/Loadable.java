package moe.ksmz.rodentraid.sck.Service.Contracts;

import java.io.FileNotFoundException;
import java.util.List;

public interface Loadable<T> {
    List<T> loadEntries() throws FileNotFoundException;
}
