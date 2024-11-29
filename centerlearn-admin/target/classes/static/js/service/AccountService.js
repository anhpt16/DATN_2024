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
    },

    _fetchAccount: async (endpoint) => {
        try {
            const response = await fetch(`${apiUrl}/accounts${endpoint}?lang=vi`, {
                method: "GET",
                headers: {"Content-Type": "application/json"},
            });

            if (response.ok) {
                return await response.json();
            } else if (response.status === 404) {
                return null;
            } else {
                throw new Error(await response.text());
            }
        } catch (error) {
            throw new Error("Error: " + error);
        }
    },
    
    getAccountById: async (id) => {
        return accountService._fetchAccount(`/id/${id}`);
    },

    getAccountByEmail: async (email) => {
        return accountService._fetchAccount(`/email/${email}`);
    },

    getAccountByPhone: async (phone) => {
        return accountService._fetchAccount(`/phone/${phone}`);
    }
}

export default accountService;