//noinspection JSUnusedGlobalSymbols
class InstanceCatalog {

    private readonly targetElement: HTMLOListElement;

    //noinspection JSUnusedGlobalSymbols
    constructor(id = "instances") {
        this.targetElement = document.getElementById(id) as HTMLOListElement;
    }

    public add(instance: Instance) {
        const instanceLi: HTMLLIElement = document.createElement("li") as HTMLLIElement;

        instanceLi.appendChild(document.createTextNode(instance.gun.name));

        this.targetElement.appendChild(instanceLi);
    }

    //noinspection JSUnusedGlobalSymbols
    public addAll(instances: Instance[] = []) {
        instances.forEach(this.add.bind(this));
    }
}
