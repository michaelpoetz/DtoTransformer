#Generic Entity to Dto Transformer
Transforms a generic Entity to its Dto using Java Reflection. The goal is to determine what is stored in the Dto using Annotations on the entity's fields (e.g. store only the Id of a referenced object). Further in the development process the Dto may be generated depending on the fields in the entity.

#TODO
* Work on the documentation
* Design Annotation
* Depending on that, figure out how to extract the desired value
