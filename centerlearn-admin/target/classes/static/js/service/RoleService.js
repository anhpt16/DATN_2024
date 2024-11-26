const apiUrl = '/api/v1';

const roleService = {
    getRole: async (page = 0, size = 10) => {
        try {
            const response = await fetch(`${apiUrl}/roles?lang=vi&page=${page}&size=${size}`, {
                method: "GET",
                headers: {"Content-Type": "application/json"},
            });
            if(!response.ok) {
                throw new Error(`HTTP error: ${response.status}`);
            }
            const data = await response.json();
            return data;
        } catch(error) {
            console.log("Error: " + error);
        }
    },
    getRoleById: async (roleId) => {
        try {
            const response = await fetch(`${apiUrl}/roles/${roleId}?lang=vi`, {
                method: "GET",
                headers: {"Content-Type": "application/json"},
            });
            if (!response.ok) {
                throw new Error(`HTTP error: ${response.status}`);
            }
            const data = await response.json();
            return data;
        } catch (error) {
            console.log("Error: " + error); 
        }
    },
    addRole: async (formData) => {
        try {
            const response = await fetch(`${apiUrl}/roles?lang=vi`, {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify(formData),
            });
            if(!response.ok) {
                throw new Error(await response.text());
            }
        } catch(error) {
            console.log("Error: " + error);
        }
    },
    deleteRole: async (roleId) => {
        try {
            const response = await fetch(`${apiUrl}/roles/${roleId}?lang=vi`, {
                method: 'DELETE'
            });
            if(!response.ok) {
                throw new Error(await response.text());
            }
        } catch(error) {
            throw new Error("Error: " + error);
        }
    },
    updateRole: async (roleId, formData) => {
        try {
            const response = await fetch(`${apiUrl}/roles/${roleId}?lang=vi`, {
                method: 'PUT',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify(formData),
            })
            if(!response.ok) {
                throw new Error(await response.text());
            }
        } catch(error) {
            throw new Error("Error: " + error);
        }
    },
}

export default roleService;