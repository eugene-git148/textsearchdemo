async function postFormDataAsJson({ url, formData }) {
	const plainFormData = Object.fromEntries(formData.entries());
	const formDataJsonString = JSON.stringify(plainFormData);

	const fetchOptions = {
		method: "POST",
                mode: "cors",
		headers: {
			"Content-Type": "application/json",
			"Accept": "application/json",
                        "Access-Control-Allow-Origin": "http://localhost:8080",
		},
		body: formDataJsonString,
	};

	const response = await fetch(url, fetchOptions);

	if (!response.ok) {
		const errorMessage = await response.text();
		throw new Error(errorMessage);
	}

	return response.json();
}

async function handleFormSubmit(event) {

	event.preventDefault();

	const form = event.currentTarget;
	const url = form.action;

	try {
		const formData = new FormData(form);
		const responseData = await postFormDataAsJson({ url, formData });
                document.getElementById("location").innerHTML = responseData.matchLocations;

		console.log({ responseData });
	} catch (error) {
		console.error(error);
               document.getElementById("location").innerHTML = error;
	}
}

function revalidate() {
  const textSearchForm = document.getElementById("textsearchform");
  textSearchForm.addEventListener("submit", handleFormSubmit);
}