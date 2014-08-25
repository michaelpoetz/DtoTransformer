#Generic Entity to Dto Transformer
Transforms a generic Entity to its Dto using Java Reflection. The goal is to determine what is stored in the Dto using Annotations on the entity's fields (e.g. store only the Id of a referenced object). Further in the development process the Dto may be generated depending on the fields in the entity.

## What's working?
* Transforms <XYZ> to <XYZ>Dto
* Simplest way works getter-to-setter (if present in the Dto)
* Defined an Annotation where you can specify a field in an Entity

Example:

    class Entity {
        private long id;
    }
In <XYZ>

    @Dto(field="id")
    private Entity entity;
will transform to the following in the <XYZ>Dto 

    private long entity; 

* Transforms Dto-annotated Lists to Lists of the type of the referenced field

## TODO
* Work on the documentation
* Design Annotation
* Depending on that, figure out how to extract the desired value
