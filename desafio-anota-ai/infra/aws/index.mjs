import {
  S3Client,
  GetObjectCommand,
  PutObjectCommand,
} from "@aws-sdk/client-s3";

const client = new S3Client({ region: "us-east-1" });

export const handler = async (event) => {
  for (const record of event.Records) {
    console.log("Iniciando processamento da mensagem: ", record);

    const rawBody = JSON.parse(record.body);
    const body = JSON.parse(rawBody.Message);
    const event = body.event;
    const ownerId = body.product ? body.product.ownerId : body.category.ownerId;

    const bucketName = "anotaai-catalog-marketplace";
    var filename = `${ownerId}-catalog.json`;

    try {
      const catalog = await getS3Object(bucketName, filename);

      const catalogData = JSON.parse(catalog);

      if (event === "CREATE_PRODUCT" || event === "UPDATE_PRODUCT") {
        updateOrAddItem(catalogData.products, body.product);
      }
      if (event === "CREATE_CATEGORY" || event === "UPDATE_CATEGORY") {
        updateOrAddItem(catalogData.categories, body.category);
      }
      if (event === "DELETE_PRODUCT") {
        deleteS3Item(catalogData.products, body.product);
      }
      if (event === "DELETE_CATEGORY") {
        deleteS3Item(catalogData.categories, body.category);
      }

      await putS3Object(bucketName, filename, JSON.stringify(catalogData));
    } catch (error) {
      if (error.message == "Error getting object from bucket") {
        const newCatalog = { products: [], categories: [] };
        if (event == "CREATE_PRODUCT") {
          newCatalog.products.push(body.product);
        } else if (event === "CREATE_CATEGORY") {
          newCatalog.categories.push(body.category);
        }

        await putS3Object(bucketName, filename, JSON.stringify(newCatalog));
      } else {
        throw error;
      }
    }
  }
};

async function getS3Object(bucket, key) {
  const getCommand = new GetObjectCommand({
    Bucket: bucket,
    Key: key,
  });

  try {
    const response = await client.send(getCommand);

    // Lendo o stream e convertendo para string
    return streamToString(response.Body);
  } catch (error) {
    throw new Error("Error getting object from bucket");
  }
}

function updateOrAddItem(catalog, newItem) {
  const index = catalog.findIndex((item) => item.id === newItem.id);
  if (index !== -1) {
    catalog[index] = { ...catalog[index], ...newItem };
  } else {
    catalog.push(newItem);
  }
}

async function putS3Object(dstBucket, dstKey, content) {
  try {
    const putCommand = new PutObjectCommand({
      Bucket: dstBucket,
      Key: dstKey,
      Body: content,
      ContentType: "application/json",
    });

    const putResult = await client.send(putCommand);

    return putResult;
  } catch (error) {
    console.log(error);
    return;
  }
}

function streamToString(stream) {
  return new Promise((resolve, reject) => {
    const chunks = [];
    stream.on("data", (chunk) => chunks.push(chunk));
    stream.on("end", () => resolve(Buffer.concat(chunks).toString("utf-8")));
    stream.on("error", reject);
  });
}

// função para deletar uma categoria ou um produto
function deleteS3Item(catalog, newItem) {
  const index = catalog.findIndex((item) => item.id === newItem.id);
  try {
    if (index !== -1) {
      catalog.splice(index, 1);
    }
  } catch (error) {
    throw new Error("Erro ao deletar um item!");
  }
}
