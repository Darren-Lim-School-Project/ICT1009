package moe.ksmz.rodentraid.sck.Service.Contracts;

import java.util.List;
import java.util.Optional;
import moe.ksmz.rodentraid.sck.Domain.Location;
import moe.ksmz.rodentraid.sck.Domain.Mice;

public interface MiceManager extends Loadable<Mice> {
    /**
     * Search for a specific mice.
     *
     * @param mouseName Name of the mouse
     * @return The found mouse, or an empty option if it does not exist
     */
    Optional<Mice> getMouse(String mouseName);

    /**
     * Get a random mice for a specified location
     *
     * @param location A {@link String} representing the location
     * @return A random mouse, or an empty option if no available mice exists
     */
    Optional<Mice> getRandomMiceForLocation(String location);

    /**
     * Get a random mice for a specified location
     *
     * @param location Name of the mouse
     * @return A random mouse, or an empty option if no available mice exists
     */
    Optional<Mice> getRandomMiceForLocation(Location location);

    /**
     * Get a list of available mice at a specific location.
     *
     * @param location A {@link String} representing the location
     * @return All mice available at the location
     */
    List<Mice> allMiceForLocation(String location);

    /**
     * Get a list of available mice at a specific location.
     *
     * @param location An instance o {@link Location}
     * @return All mice available at the location
     */
    List<Mice> allMiceForLocation(Location location);

    /**
     * A list of all available mice
     *
     * @return The list of mice
     */
    List<Mice> all();
}
