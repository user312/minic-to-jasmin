package it.m2j;

public enum IdType {
	INT {
			public String toString() 
			{
				return "int"; 
			}
	}, 
	FLOAT{
		public String toString() 
		{
			return "float"; 
		}
	}, 
	BOOL{
		public String toString() 
		{
			return "bool"; 
		}
	}, 
	STRING{
		public String toString() 
		{
			return "string"; 
		}
	}, 
	NULL{
		public String toString() 
		{
			return "void"; 
		}
	} 
}
