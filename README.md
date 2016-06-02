# ApiDeserializer

Type Adapter for registering the gson object using retrofit 2 for parsing json objects.

Implemented as a 'woarkaround" due to the fact that in the moment of implementation the APIs returned 2XX code despite it was an error.

Each Response must implement ApiResponseResult interface which informs whether call was successfully processed or not

