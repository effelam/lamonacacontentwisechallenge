#!/bin/bash
echo "Importing JSON data into MongoDB..."

for file in /data/*.json; do
  collection_name=$(basename "$file" .json)
  echo "Importing $file into collection $collection_name..."
  mongoimport --db contentwisedb --collection "$collection_name" --file "$file" --jsonArray
done

echo "Import completed!"