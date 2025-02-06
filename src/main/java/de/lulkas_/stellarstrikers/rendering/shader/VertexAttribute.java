package de.lulkas_.stellarstrikers.rendering.shader;

public record VertexAttribute(int size, int type, boolean normalized, long pointerBufferOffset) {
    public VertexAttribute {
        if (size > 4 || size < 1) {
            throw new RuntimeException("Wrong Vertex Attribute Format");
        }
        if (!((type >= 0x1400 && type <= 0x1406) || type == 0x140a)) {
            throw new RuntimeException("Wrong Vertex Attribute Format");
        }
    }

    public int getBytes() {
        return getSingleValueBytes() * this.size;
    }

    private int getSingleValueBytes() {
        int toReturn;
        switch (this.type) {
            case 0x1400, 0x1401:
                toReturn = 1;
                break;
            case 0x1402, 0x1403:
                toReturn = 2;
                break;
            case 0x1404, 0x1405, 0x1406:
                toReturn = 4;
                break;
            case 0x140a:
                toReturn = 8;
                break;
            default:
                throw new RuntimeException(new IllegalStateException("Unsupported type: " + type));
        }

        return toReturn;
    }
}
