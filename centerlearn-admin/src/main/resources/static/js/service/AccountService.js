const apiUrl = "/api/v1";

const accountService = {
    getAccountFilter: async (queryString) => {
        try {
            const response = await fetch(`${apiUrl}/accounts?lang=vi${queryString}`, {
                method: "GET",
                headers: {"Content-Type": "application/json"},
            })
            if(!response.ok) {
                throw new Error(await response.text());
            }
            const data = await response.json();
            return data;
        } catch (error) {
            throw new Error("Error: " + error);
        }
    }
}

export default accountService;