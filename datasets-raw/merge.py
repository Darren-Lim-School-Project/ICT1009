import csv

populations = open("populations.csv")
po = list(csv.DictReader(populations))


def find_location(mice: str) -> list:
    return {r["Location"] for r in po if r["Mouse"] == mice}


with open("mouse-power-effs.csv") as powereff:
    eff = csv.DictReader(powereff)

    res = []

    for row in eff:
        newRow = {}
        newRow["mouse"] = row["Mouse"]
        newRow["power"] = row["Power"]
        newRow["hydro"] = row["Hydro"]
        newRow["physical"] = row["Physical"]
        newRow["shadow"] = row["Shadow"]
        newRow["tactical"] = row["Tactical"]
        newRow["locations"] = find_location(row["Mouse"])
        res.append(newRow)

    f = open("out.csv", "w")
    f.write("mouse,power,physical,tactical,shadow,hydro,locations\n")
    for l in res:
        if len(l["locations"]) > 0:
            f.write("{},{},{},{},{},{},\"{}\"\n".format(
                l["mouse"], l["power"], l["physical"], l["tactical"],
                l["shadow"], l["hydro"], ",".join(l["locations"])))
