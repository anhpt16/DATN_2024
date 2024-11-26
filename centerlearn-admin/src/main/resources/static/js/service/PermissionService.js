const apiUrl = "/api/v1";

const permissionService = {
    addPermission: async (formData) => {
        try {
            const response = await fetch(`${apiUrl}/permissions/add?lang=vi`, {
                method: "POST",
                headers: {"Content-Type": "application/json"},
                body: JSON.stringify(formData),
            });
            if(!response.ok) {
                throw new Error(await response.text());
            }
        } catch (error) {
            console.log("Error: " + error);
        }
    },
    deletePermission: async (formData) => {
        try {
            const response = await fetch(`${apiUrl}/permissions/delete?lang=vi`, {
                method: "DELETE",
                headers: {"Content-Type": "application/json"},
                body: JSON.stringify(formData),
            })
            if(!response.ok) {
                throw new Error(await response.text());
            }
        } catch (error) {
            throw new Error("Error: " + error);
        }
    },
    filterPermissionByType: async (queryString) => {
        try {
            const response = await fetch(`${apiUrl}/permissions/by-type?lang=vi${queryString}`, {
                method: "GET",
                headers: {"Content-Type": "application/json"},
            })
            if (!response.ok) {
                throw new Error(`HTTP error: ${response.status}`);
            }
            const data = await response.json();
            return data;
        } catch (error) {
            console.log("Error: " + error);
        }
    }
}

export default permissionService;