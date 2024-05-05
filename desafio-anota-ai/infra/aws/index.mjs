export const handler = async (event) => {
  for (const record of event.Records) {
    console.log("Record: ", record);

    const rawBody = JSON.parse(record.body);
    const body = JSON.parse(rawBody.Message);

    console.log("Record.rawBody: " + rawBody);
    console.log("Record.body: " + body);
  }
};
